package com.example.testapplication.presentation.listQuote

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
    }

    data class State(
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

    fun create(): ListQuoteStore =
        object : ListQuoteStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ListQuoteStore",
            initialState = State(
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

        data class LoadingSuccess(val quotes: List<Quote>) : Action
    }

    private sealed interface Msg {
        //действия при которых меняется стейт экрана
        data object StartLoading : Msg

        data object LoadingError : Msg

        data class LoadingSuccess(val quotes: List<Quote>) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(Action.StartLoading)
                try {
                    getAllQuotesUseCase().collect {
                        dispatch(Action.LoadingSuccess(quotes = it))
                    }
                } catch (e: Exception) {
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
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.LoadingError -> {
                    dispatch(Msg.LoadingError)
                }

                is Action.LoadingSuccess -> {
                    dispatch(Msg.LoadingSuccess(action.quotes))
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
