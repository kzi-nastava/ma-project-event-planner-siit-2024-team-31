package com.example.ep2024.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ep2024.databinding.FragmentEditServiceBinding
import com.example.ep2024.domain.model.EventType
import com.example.ep2024.domain.model.Service
import com.example.ep2024.domain.model.service.ChangeEntry
import com.example.ep2024.domain.model.service.ConfirmationMethod
import com.example.ep2024.domain.model.service.ServiceChangeHistory
import java.util.Date

/**
 * A simple [Fragment] subclass.
 * Use the [EditServiceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditServiceFragment : Fragment() {

    private var _binding: FragmentEditServiceBinding? = null
    private val binding get() = _binding!!

    private lateinit var service: Service
    private lateinit var eventTypes: List<EventType>
    private val selectedEventTypes = mutableListOf<String>() // IDs типов событий

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            service = it.getParcelable("service")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentEditServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        populateServiceData()

        //TODO:
        loadEventTypes()

        binding.buttonUploadImages.setOnClickListener {
        }

        binding.buttonSaveService.setOnClickListener {
            saveServiceChanges()
        }

        binding.buttonDeleteService.setOnClickListener {
            confirmAndDeleteService()
        }
    }

    private fun populateServiceData() {
        binding.editTextServiceName.setText(service.name)
        binding.editTextServiceDescription.setText(service.description)
        binding.editTextServiceCharacteristics.setText(service.characteristics)
        binding.editTextServicePrice.setText(service.price.toString())
        binding.editTextServiceDiscount.setText(service.discount.toString())
        binding.textViewServiceCategory.text = "Категория: ${getCategoryNameById(service.categoryId)}"

        binding.switchServiceVisibility.isChecked = service.visibility
        binding.switchServiceAvailability.isChecked = service.availability

        binding.editTextBookingDuration.setText(service.bookingDuration?.toString() ?: "")
        binding.editTextMinParticipation.setText(service.minParticipation?.toString() ?: "")
        binding.editTextMaxParticipation.setText(service.maxParticipation?.toString() ?: "")
        binding.editTextBookingPeriod.setText(service.bookingPeriod.toString())
        binding.editTextCancellationPeriod.setText(service.cancellationPeriod.toString())

        if (service.confirmationMethod == ConfirmationMethod.AUTOMATIC) {
            binding.radioButtonAutomatic.isChecked = true
        } else {
            binding.radioButtonManual.isChecked = true
        }

        selectedEventTypes.addAll(service.eventTypes)
    }

    private fun getCategoryNameById(categoryId: String): String {
        return when (categoryId) {
            "1" -> "Кейтеринг"
            "2" -> "Декорации"
            else -> "Другое"
        }
    }

    private fun loadEventTypes() {
        eventTypes = listOf(
            EventType(id = "1", name = "Свадьба"),
            EventType(id = "2", name = "Конференция"),
            EventType(id = "3", name = "День рождения")
        )

        eventTypes.forEach { eventType ->
            val checkBox = CheckBox(requireContext()).apply {
                text = eventType.name
                isChecked = selectedEventTypes.contains(eventType.id)
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        selectedEventTypes.add(eventType.id)
                    } else {
                        selectedEventTypes.remove(eventType.id)
                    }
                }
            }
            binding.linearLayoutEventTypes.addView(checkBox)
        }
    }

    private fun saveServiceChanges() {
        val name = binding.editTextServiceName.text.toString().trim()
        val description = binding.editTextServiceDescription.text.toString().trim()
        val characteristics = binding.editTextServiceCharacteristics.text.toString().trim()
        val priceText = binding.editTextServicePrice.text.toString().trim()
        val discountText = binding.editTextServiceDiscount.text.toString().trim()
        val bookingDurationText = binding.editTextBookingDuration.text.toString().trim()
        val minParticipationText = binding.editTextMinParticipation.text.toString().trim()
        val maxParticipationText = binding.editTextMaxParticipation.text.toString().trim()
        val bookingPeriodText = binding.editTextBookingPeriod.text.toString().trim()
        val cancellationPeriodText = binding.editTextCancellationPeriod.text.toString().trim()
        val visibility = binding.switchServiceVisibility.isChecked
        val availability = binding.switchServiceAvailability.isChecked
        val confirmationMethod = if (binding.radioButtonAutomatic.isChecked) {
            ConfirmationMethod.AUTOMATIC
        } else {
            ConfirmationMethod.MANUAL
        }

        if (name.isEmpty() || description.isEmpty() || characteristics.isEmpty() || priceText.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Пожалуйста, заполните все обязательные поля.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (selectedEventTypes.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Пожалуйста, выберите хотя бы один тип события.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val price = priceText.toDoubleOrNull() ?: 0.0
        val discount = discountText.toDoubleOrNull() ?: 0.0

        val updatedService = service.copy(
            name = name,
            description = description,
            characteristics = characteristics,
            price = price,
            discount = discount,
            eventTypes = selectedEventTypes,
            visibility = visibility,
            availability = availability,
            bookingDuration = bookingDurationText.toIntOrNull(),
            minParticipation = minParticipationText.toIntOrNull(),
            maxParticipation = maxParticipationText.toIntOrNull(),
            bookingPeriod = bookingPeriodText.toIntOrNull() ?: service.bookingPeriod,
            cancellationPeriod = cancellationPeriodText.toIntOrNull() ?: service.cancellationPeriod,
            confirmationMethod = confirmationMethod
        )

        //TODO:
        saveServiceToServer(updatedService)

        recordServiceChangeHistory(service, updatedService)
    }

    private fun saveServiceToServer(updatedService: Service) {
        // Отправляем обновленный объект на сервер
        // Обработка ответа и ошибок
        Toast.makeText(requireContext(), "Изменения сохранены.", Toast.LENGTH_SHORT).show()
        // Возвращаемся назад или обновляем интерфейс
    }

    private fun recordServiceChangeHistory(oldService: Service, newService: Service) {
        val changes = mutableListOf<ChangeEntry>()

        if (oldService.name != newService.name) {
            changes.add(ChangeEntry("name", oldService.name, newService.name))
        }
        if (oldService.description != newService.description) {
            changes.add(ChangeEntry("description", oldService.description, newService.description))
        }
        // Проверяем остальные поля аналогичным образом

        if (changes.isNotEmpty()) {
            val changeHistory = ServiceChangeHistory(
                serviceId = oldService.id,
                changedBy = getCurrentUserId(),
                changeDate = Date(),
                changes = changes
            )
            // Сохраняем историю изменений на сервере
            saveChangeHistoryToServer(changeHistory)
        }
    }

    private fun saveChangeHistoryToServer(changeHistory: ServiceChangeHistory) {
        // Отправляем объект истории изменений на сервер
    }

    private fun getCurrentUserId(): String {
        // Возвращаем ID текущего пользователя
        return "PUP123" // Заглушка
    }

    private fun confirmAndDeleteService() {
        // Проверяем наличие будущих бронирований
        if (hasFutureBookings(service.id)) {
            Toast.makeText(
                requireContext(),
                "Невозможно удалить услугу, имеются будущие бронирования.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        // Показываем диалог подтверждения
        AlertDialog.Builder(requireContext())
            .setTitle("Удалить услугу")
            .setMessage("Вы уверены, что хотите удалить эту услугу?")
            .setPositiveButton("Удалить") { _, _ ->
                // Выполняем логическое удаление услуги
                deleteService()
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun hasFutureBookings(serviceId: String): Boolean {
        // Проверяем на сервере наличие будущих бронирований
        // Для примера вернем false
        return false
    }

    private fun deleteService() {
        // Выполняем логическое удаление услуги на сервере
        Toast.makeText(requireContext(), "Услуга удалена.", Toast.LENGTH_SHORT).show()
        // Возвращаемся назад
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}