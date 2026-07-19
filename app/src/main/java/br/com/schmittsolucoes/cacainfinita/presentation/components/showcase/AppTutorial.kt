package br.com.schmittsolucoes.cacainfinita.presentation.components.showcase

import android.content.Context
import br.com.schmittsolucoes.cacainfinita.R

sealed interface AppTutorial {
    fun getSteps(context: Context): List<ShowcaseStep>

    data object Home : AppTutorial {
        const val USER_LEVEL_CARD = "user_level_card"
        const val PERSONAL_RECORDS_SECTION = "personal_records_section"
        const val BOTTOM_NAV_BAR = "bottom_nav_bar"

        override fun getSteps(context: Context) = listOf(
            ShowcaseStep(
                targetId = USER_LEVEL_CARD,
                text = context.getString(R.string.tutorial_home_level_card)
            ),
            ShowcaseStep(
                targetId = PERSONAL_RECORDS_SECTION,
                text = context.getString(R.string.tutorial_home_records_section)
            ),
            ShowcaseStep(
                targetId = BOTTOM_NAV_BAR,
                text = context.getString(R.string.tutorial_home_bottom_nav)
            )
        )
    }

    data object WordSearchList : AppTutorial {
        const val ADD_PUZZLE_FAB = "add_puzzle_fab"

        override fun getSteps(context: Context) = listOf(
            ShowcaseStep(
                targetId = ADD_PUZZLE_FAB,
                text = context.getString(R.string.tutorial_puzzles_add)
            )
        )
    }

    data object WordSearchItem : AppTutorial {
        const val PUZZLE_ITEM_STATUS = "puzzle_item_status"
        const val PUZZLE_ITEM_DELETE = "puzzle_item_delete"

        override fun getSteps(context: Context) = listOf(
            ShowcaseStep(
                targetId = PUZZLE_ITEM_STATUS,
                text = context.getString(R.string.tutorial_puzzles_item_status)
            ),
            ShowcaseStep(
                targetId = PUZZLE_ITEM_DELETE,
                text = context.getString(R.string.tutorial_puzzles_item_delete)
            )
        )
    }

    data object PuzzleGame : AppTutorial {
        const val PUZZLE_TIMER = "puzzle_timer"
        const val PUZZLE_GRID = "puzzle_grid"
        const val PUZZLE_WORDS_FAB = "puzzle_words_fab"

        override fun getSteps(context: Context) = listOf(
            ShowcaseStep(
                targetId = PUZZLE_TIMER,
                text = context.getString(R.string.tutorial_puzzle_timer)
            ),
            ShowcaseStep(
                targetId = PUZZLE_GRID,
                text = context.getString(R.string.tutorial_puzzle_gestures)
            ),
            ShowcaseStep(
                targetId = PUZZLE_WORDS_FAB,
                text = context.getString(R.string.tutorial_puzzle_words_list)
            ),
            ShowcaseStep(
                targetId = PUZZLE_GRID,
                text = context.getString(R.string.tutorial_puzzle_pause)
            )
        )
    }
}
