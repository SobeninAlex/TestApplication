package com.example.testapplication.presentation.listQuote

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

class DefaultListQuoteComponent @AssistedInject constructor(
    private val storeFactory: ListQuoteStoreFactory,
    @Assisted("onQuoteItemClicked") private val onQuoteItemClicked: (Int) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext,
) : ListQuoteComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<ListQuoteStore.State> = store.stateFlow

    override fun onClickQuoteItem(quoteId: Int) {
        store.accept(ListQuoteStore.Intent.QuoteItemClicked(quoteId))
    }

    override fun loadNextBatch() {
        store.accept(ListQuoteStore.Intent.LoadNextBatch)
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onQuoteItemClicked") onQuoteItemClicked: (Int) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultListQuoteComponent
    }

}