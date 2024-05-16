package com.example.testapplication.presentation.detailQuote

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.testapplication.domain.entity.Quote
import com.example.testapplication.domain.usecase.GetQuoteDetailUseCase
import com.example.testapplication.presentation.detailQuote.DetailQuoteStore.Intent
import com.example.testapplication.presentation.detailQuote.DetailQuoteStore.Label
import com.example.testapplication.presentation.detailQuote.DetailQuoteStore.State
import kotlinx.coroutines.launch
import javax.inject.Inject

interface DetailQuoteStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickBack : Intent
    }

    data class State(
        val quoteId: Int,
        val detailQuoteState: DetailQuoteState
    ) {

        sealed interface DetailQuoteState {

            data object Initial : DetailQuoteState

            data object StartLoading : DetailQuoteState

            data object LoadingError : DetailQuoteState

            data class LoadingSuccess(val quote: Quote) : DetailQuoteState

        }

    }

    sealed interface Label {

        data object ClickBack : Label

    }
}

class DetailQuoteStoreFactory @Inject constructor (
    private val storeFactory: StoreFactory,
    private val getQuoteDetailUseCase: GetQuoteDetailUseCase
) {

    fun create(quoteId: Int): DetailQuoteStore =
        object : DetailQuoteStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailQuoteStore",
            initialState = State(
                quoteId = quoteId,
                detailQuoteState = State.DetailQuoteState.Initial
            ),
            bootstrapper = BootstrapperImpl(quoteId),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {

        data object StartLoading : Action

        data object LoadingError : Action

        data class LoadingSuccess(val quote: Quote) : Action

    }

    private sealed interface Msg {

        data object StartLoading : Msg

        data object LoadingError : Msg

        data class LoadingSuccess(val quote: Quote) : Msg

    }

    private inner class BootstrapperImpl(private val quoteId: Int) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                dispatch(Action.StartLoading)
                try {
                    val quote = getQuoteDetailUseCase(quoteId)
                    dispatch(Action.LoadingSuccess(quote))
                } catch (e: Exception) {
                    dispatch(Action.LoadingError)
                }
            }
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.LoadingError -> {
                    dispatch(Msg.LoadingError)
                }

                is Action.LoadingSuccess -> {
                    dispatch(Msg.LoadingSuccess(action.quote))
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
                    detailQuoteState = State.DetailQuoteState.LoadingError
                )
            }

            is Msg.LoadingSuccess -> {
                copy(
                    detailQuoteState = State.DetailQuoteState.LoadingSuccess(msg.quote)
                )
            }

            is Msg.StartLoading -> {
                copy(
                    detailQuoteState = State.DetailQuoteState.StartLoading
                )
            }
        }
    }
}
