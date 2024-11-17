import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SuggestionStatus : Parcelable {
    PENDING,
    APPROVED,
    REJECTED
}