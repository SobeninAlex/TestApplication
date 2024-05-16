package com.example.testapplication.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.example.testapplication.presentation.detailQuote.DefaultDetailQuoteComponent
import com.example.testapplication.presentation.listQuote.DefaultListQuoteComponent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.parcelize.Parcelize

class DefaultRootComponent @AssistedInject constructor(
    private val listQuoteComponentFactory: DefaultListQuoteComponent.Factory,
    private val detailQuoteComponentFactory: DefaultDetailQuoteComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.ListQuote,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ) : RootComponent.Child {
        return when (config) {
            is Config.DetailQuote -> {
                val component = detailQuoteComponentFactory.create(
                    quoteId = config.quoteId,
                    onBackClicked = {
                        navigation.pop()
                    },
                    componentContext = componentContext
                )
                RootComponent.Child.DetailQuote(component)
            }

            is Config.ListQuote -> {
                val component = listQuoteComponentFactory.create(
                    onQuoteItemClicked = {
                        navigation.push(Config.DetailQuote(it))
                    },
                    componentContext = componentContext
                )
                RootComponent.Child.ListQuote(component)
            }
        }
    }

    private sealed interface Config : Parcelable {

        @Parcelize
        data object ListQuote : Config

        @Parcelize
        data class DetailQuote(val quoteId: Int) : Config

    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultRootComponent
    }

}