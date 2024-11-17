import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class CategorySuggestion(
    val id: String = UUID.randomUUID().toString(),
    val suggestedBy: String, // ID PUP
    val name: String,
    val description: String,
    val status: SuggestionStatus = SuggestionStatus.PENDING
) : Parcelable