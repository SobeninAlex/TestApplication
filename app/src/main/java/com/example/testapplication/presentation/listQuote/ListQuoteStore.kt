package com.example.testapplication.presentation.listQuote

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.domain.usecase.GetAllQuotesUseCase
import com.example.testapplication.presentation.listQuote.ListQuoteStore.Intent
import com.example.testapplication.presentation.listQuote.ListQuoteStore.Label
import com.example.testapplication.presentation.listQuote.ListQuoteStore.State
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ListQuoteStore : Store<Intent, State, Label> {

    sealed interface Intent {
        //действия, которые может совершить пользователь
        data class QuoteItemClicked(val quoteId: Int) : Intent

        data object LoadNextBatch : Intent
    }

    data class State(
        val isLoadingMore: Boolean,
        val listQuoteState: ListQuoteState
    ) {
        sealed interface ListQuoteState {

            data object Initial : ListQuoteState

            data object StartLoading : ListQuoteState

            data object LoadingError : ListQuoteState

            data class LoadingSuccess(val quotes: List<Quote>) : ListQuoteState
        }
    }

    sealed interface Label {
        //действия при которых происходит навигация
        data class QuoteItemClicked(val quoteId: Int) : Label
    }
}

class ListQuoteStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getAllQuotesUseCase: GetAllQuotesUseCase,
) {

    private val _listQuotes = mutableListOf<Quote>()
    private var offset = 1
    private val limit = 10

    fun create(): ListQuoteStore =
        object : ListQuoteStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ListQuoteStore",
            initialState = State(
                isLoadingMore = true,
                listQuoteState = State.ListQuoteState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        //загрузка из репозитория при создании FavouriteStore
        data object StartLoading : Action

        data object LoadingError : Action

        data class LoadingSuccess(val isLoadingMore: Boolean, val quotes: List<Quote>) : Action
    }

    private sealed interface Msg {
        //действия при которых меняется стейт экрана
        data object StartLoading : Msg

        data object LoadingError : Msg

        data class LoadingSuccess(val isLoadingMore: Boolean, val quotes: List<Quote>) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(Action.StartLoading)
                try {
                    getAllQuotesUseCase(offset = offset).collect {
                        _listQuotes.addAll(it)
                        val isLoadingMore = it.size == 10
                        dispatch(Action.LoadingSuccess(quotes = _listQuotes.toList(), isLoadingMore = isLoadingMore))
                        offset += limit
                    }
                } catch (e: Exception) {
                    Log.d("ListQuoteStore", e.stackTraceToString())
                    dispatch(Action.LoadingError)
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.QuoteItemClicked -> {
                    publish(Label.QuoteItemClicked(quoteId = intent.quoteId))
                }

                is Intent.LoadNextBatch -> {
                    scope.launch {
                        try {
                            getAllQuotesUseCase(offset = offset).collect {
                                _listQuotes.addAll(it)
                                val isLoadingMore = it.size == 10
                                dispatch(Msg.LoadingSuccess(quotes = _listQuotes.toList(), isLoadingMore = isLoadingMore))
                                offset += limit
                            }
                        } catch (e: Exception) {
                            Log.d("ListQuoteStore", e.stackTraceToString())
                            dispatch(Msg.LoadingError)
                        }
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.LoadingError -> {
                    dispatch(Msg.LoadingError)
                }

                is Action.LoadingSuccess -> {
                    dispatch(Msg.LoadingSuccess(quotes = action.quotes, isLoadingMore = action.isLoadingMore))
                }

                is Action.StartLoading -> {
                    dispatch(Msg.StartLoading)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.LoadingError -> {
                copy(
                    listQuoteState = State.ListQuoteState.LoadingError
                )
            }

            is Msg.LoadingSuccess -> {
                copy(
                    isLoadingMore = msg.isLoadingMore,
                    listQuoteState = State.ListQuoteState.LoadingSuccess(msg.quotes)
                )
            }

            is Msg.StartLoading -> {
                copy(
                    listQuoteState = State.ListQuoteState.StartLoading
                )
            }
        }
    }
}
