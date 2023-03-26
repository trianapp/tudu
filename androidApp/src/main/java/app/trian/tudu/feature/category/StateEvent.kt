package app.trian.tudu.feature.category

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import app.trian.tudu.data.model.CategoryWithCount
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
@Immutable
data class CategoryState(
    val categoryName: String = "",
    val categoryId: String = "",
    val showFormCategory: Boolean = false,
    val showDialogDeleteCategory: Boolean = false,
) : Parcelable

@Parcelize
@Immutable
data class CategoryDataState(
    val category: @RawValue List<CategoryWithCount> = listOf()
) : Parcelable

@Immutable
sealed class CategoryEvent {
    data class SetCategoryName(val categoryName: String) : CategoryEvent()
    data class SetUpdateCategory(val categoryName: String, val categoryId: String) : CategoryEvent()
    object SubmitCategory : CategoryEvent()
    object DeleteCategory : CategoryEvent()
    data class ShowFormCategory(val isShow: Boolean) : CategoryEvent()
    data class ShowDialogDeleteCategory(val isShow: Boolean) : CategoryEvent()
}