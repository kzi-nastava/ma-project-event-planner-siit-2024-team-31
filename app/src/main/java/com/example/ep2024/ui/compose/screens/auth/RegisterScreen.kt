package com.example.ep2024.ui.compose.screens.auth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.domain.entity.user.Role
import com.example.ep2024.ui.compose.components.textFields.FormTextField
import com.example.ep2024.ui.compose.components.screenHeaders.DefaultScreenHeader
import com.example.ep2024.ui.viewModels.auth.RegisterViewModel
import com.example.ep2024.ui.viewModels.auth.types.RegisterEvent
import com.example.ep2024.ui.viewModels.auth.types.RegisterFormState
import com.example.ep2024.ui.viewModels.auth.types.RegisterResult
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegistrationSuccess: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val profilePicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        viewModel.onEvent(RegisterEvent.ProfileImagePicked(uri))
    }
    val portfolioPicker = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris: List<Uri> ->
        if (uris.isNotEmpty()) viewModel.onEvent(RegisterEvent.PortfolioImagesPicked(uris))
    }

    LaunchedEffect(Unit) {
        viewModel.registerResult.collectLatest { result ->
            when (result) {
                is RegisterResult.Success -> {
                    snackbarHostState.showSnackbar("Registration successful. Check email.")
                    onRegistrationSuccess()
                }
                is RegisterResult.Error -> snackbarHostState.showSnackbar(result.message)
            }
        }
    }

    // Visibility logic
    val role = state.role
    val showPersonal = role == Role.USER || role == Role.OD
    val showCompany = role == Role.PUP
    val showSinglePhoto = role == Role.USER
    val showMultiPhoto = role == Role.OD || role == Role.PUP

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            DefaultScreenHeader("Register", "Create a new account to get started")
            AccountSection(state, onEvent = viewModel::onEvent)
            RoleSection(role = role, onRoleChange = { viewModel.onEvent(RegisterEvent.RoleChanged(it)) })
            if (showPersonal) PersonalSection(state, onEvent = viewModel::onEvent)
            if (showCompany) {
                CompanySection(state, onEvent = viewModel::onEvent)
                CompanyDescriptionSection(state, onEvent = viewModel::onEvent)
            }

            AddressSection(state, onEvent = viewModel::onEvent)

            if (showSinglePhoto || showMultiPhoto) PhotosSection(
                state = state,
                showSinglePhoto = showSinglePhoto,
                showMultiPhoto = showMultiPhoto,
                onPickProfile = { profilePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                onPickPortfolio = { portfolioPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
            )

            SubmitSection(isLoading = state.isLoading) { viewModel.onEvent(RegisterEvent.Register) }
        }
        if (state.isLoading) CircularProgressIndicator(Modifier.align(Alignment.Center))
        SnackbarHost(snackbarHostState, Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
private fun SectionContainer(title: String, subtitle: String? = null, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.small
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold))
            if (subtitle != null) Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            content()
        }
    }
}

@Composable
private fun AccountSection(state: RegisterFormState, onEvent: (RegisterEvent) -> Unit) {
    SectionContainer(title = "Account", subtitle = "Account credentials") {
        FormTextField(
            value = state.email,
            label = "Email",
            onValueChange = { onEvent(RegisterEvent.EmailChanged(it)) }
        )
        FormTextField(
            value = state.password,
            label = "Password",
            password = true,
            onValueChange = { onEvent(RegisterEvent.PasswordChanged(it)) }
        )
        FormTextField(
            value = state.confirmPassword,
            label = "Confirm Password",
            password = true,
            onValueChange = { onEvent(RegisterEvent.ConfirmPasswordChanged(it)) }
        )
    }
}

@Composable
private fun RoleSection(role: Role, onRoleChange: (Role) -> Unit) {
    SectionContainer(title = "Account Type", subtitle = "Select your role") {
        RoleCards(selected = role, onSelect = onRoleChange)
    }
}

@Composable
private fun RoleCards(selected: Role, onSelect: (Role) -> Unit) {
    val roles = listOf(
        Triple(Role.USER, Icons.Filled.Person, "Basic User"),
        Triple(Role.OD, Icons.Filled.Event, "Event Organizer"),
        Triple(Role.PUP, Icons.Filled.Store, "Service and Product Provider")
    )
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        roles.forEach { (r, icon, label) ->
            val isSelected = r == selected
            val bg = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
            val fg = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
            Card(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 90.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(bg)
                    .clickable { onSelect(r) },
                colors = CardDefaults.cardColors(containerColor = bg),
                elevation = CardDefaults.cardElevation(if (isSelected) 6.dp else 1.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(icon, contentDescription = label, tint = fg)
                    Text(label, style = MaterialTheme.typography.labelMedium, color = fg)
                }
            }
        }
    }
}

@Composable
private fun PersonalSection(state: RegisterFormState, onEvent: (RegisterEvent) -> Unit) {
    SectionContainer(title = "Personal information", subtitle = "Information about you") {
        FormTextField(
            value = state.firstName,
            onValueChange = { onEvent(RegisterEvent.FirstNameChanged(it)) },
            label = "Name"
        )
        FormTextField(
            value = state.lastName.orEmpty(),
            onValueChange = { onEvent(RegisterEvent.LastNameChanged(it)) },
            label = "Last Name"
        )
        FormTextField(
            value = state.phoneNumber.orEmpty(),
            onValueChange = { onEvent(RegisterEvent.PhoneNumberChanged(it)) },
            label = "Phone number"
        )
    }
}

@Composable
private fun CompanySection(state: RegisterFormState, onEvent: (RegisterEvent) -> Unit) {
    SectionContainer(title = "Company", subtitle = "Organization details") {
        FormTextField(
            value = state.companyName.orEmpty(),
            onValueChange = { onEvent(RegisterEvent.CompanyNameChanged(it)) },
            label = "Company Name"
        )
        FormTextField(
            value = state.phoneNumber.orEmpty(),
            onValueChange = { onEvent(RegisterEvent.CompanyPhoneNumberChanged(it)) },
            label = "Company Phone number"
        )
    }
}

@Composable
private fun PhotosSection(
    state: RegisterFormState,
    showSinglePhoto: Boolean,
    showMultiPhoto: Boolean,
    onPickProfile: () -> Unit,
    onPickPortfolio: () -> Unit
) {
    SectionContainer(title = "Photo", subtitle = "Add your photos") {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            if (showSinglePhoto) {
                Button(onClick = onPickProfile) { Text("Profile avatar") }
            }
            if (showMultiPhoto) {
                Button(onClick = onPickPortfolio) { Text("Business photos") }
            }
        }
        AnimatedVisibility(visible = state.photos.isNotEmpty(), enter = fadeIn(), exit = fadeOut()) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.photos) { uri ->
                    Image(
                        painter = rememberAsyncImagePainter(model = uri),
                        contentDescription = null,
                        modifier = Modifier
                            .size(84.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
private fun CompanyDescriptionSection(state: RegisterFormState, onEvent: (RegisterEvent) -> Unit) {
    SectionContainer(title = "Description", subtitle = "Describe your business") {
        FormTextField(
            value = state.description.orEmpty(),
            onValueChange = { onEvent(RegisterEvent.DescriptionChanged(it)) },
            label = "Description"
        )
    }
}

@Composable
private fun AddressSection(state: RegisterFormState, onEvent: (RegisterEvent) -> Unit) {
    SectionContainer(title = "Address", subtitle = "Location details") {
        FormTextField(
            value = state.country.orEmpty(),
            onValueChange = { onEvent(RegisterEvent.CountryChanged(it)) },
            label = "Country"
        )
        FormTextField(
            value = state.city.orEmpty(),
            onValueChange = { onEvent(RegisterEvent.CityChanged(it)) },
            label = "City"
        )
        FormTextField(
            value = state.address.orEmpty(),
            onValueChange = { onEvent(RegisterEvent.AddressChanged(it)) },
            label = "Address"
        )
        FormTextField(
            value = state.zipCode.orEmpty(),
            onValueChange = { onEvent(RegisterEvent.ZipCodeChanged(it)) },
            label = "Zip Code"
        )
    }
}

@Composable
private fun SubmitSection(isLoading: Boolean, onSubmit: () -> Unit) {
    Box(Modifier.fillMaxWidth()) {
        val gradient = Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary))
        Button(
            onClick = onSubmit,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient, shape = MaterialTheme.shapes.medium)
        ) { Text(if (isLoading) "Loading..." else "Sign Up") }
    }
}
