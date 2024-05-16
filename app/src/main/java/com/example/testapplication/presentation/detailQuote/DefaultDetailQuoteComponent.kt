package com.example.testapplication.presentation.detailQuote

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.testapplication.utill.componentScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultDetailQuoteComponent @AssistedInject constructor(
    private val storeFactory: DetailQuoteStoreFactory,
    @Assisted("quoteId") private val quoteId: Int,
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext,
) : DetailQuoteComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create(quoteId) }
    private val scope = componentScope()

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    is DetailQuoteStore.Label.ClickBack -> {
                        onBackClicked()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<DetailQuoteStore.State> = store.stateFlow

    override fun onClickBack() {
        store.accept(DetailQuoteStore.Intent.ClickBack)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("quoteId") quoteId: Int,
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultDetailQuoteComponent
    }

}