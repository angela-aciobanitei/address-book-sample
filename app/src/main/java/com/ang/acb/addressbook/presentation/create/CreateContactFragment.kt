package com.ang.acb.addressbook.presentation.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.ang.acb.addressbook.R
import com.ang.acb.addressbook.databinding.FragmentCreateContactBinding
import com.ang.acb.addressbook.presentation.utils.EventObserver
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateContactFragment : Fragment(R.layout.fragment_create_contact) {

    private val viewModel: CreateContactViewModel by viewModels()

    private var _binding: FragmentCreateContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
        saveContact()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeData() {
        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = it
            binding.saveContactButton.isEnabled = !it
        })

        viewModel.message.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            val navHostFragment = requireActivity().supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

            when (it) {
                is CreateContactViewModel.Navigation.Up -> navController.navigateUp()
            }
        })
    }

    // todo refactor it later
    private fun saveContact() {
        binding.saveContactButton.setOnClickListener {
            val invalidFirstName = binding.tietFirstName.text.isNullOrBlank()
            val invalidLastName = binding.tietLastName.text.isNullOrBlank()
            val invalidEmail = binding.tietEmail.text.isNullOrBlank() ||
                    !viewModel.isValidEmail(binding.tietEmail.text.toString())
            val invalidPhoneNumber = binding.tietPhoneNumber.text.isNullOrBlank()
            val invalidAddress = binding.tietAddress.text.isNullOrBlank()
            val requiredMessage = getString(R.string.required_field_error)

            if (invalidFirstName) binding.tietFirstName.error = requiredMessage
            if (invalidLastName) binding.tietLastName.error = requiredMessage
            if (invalidEmail) binding.tietEmail.error = getString(R.string.invalid_email_error)
            if (invalidPhoneNumber) binding.tietPhoneNumber.error = requiredMessage
            if (invalidAddress) binding.tietAddress.error = requiredMessage

            val invalidCredentials = listOf(
                invalidFirstName,
                invalidLastName,
                invalidEmail,
                invalidPhoneNumber,
                invalidAddress
            ).any { it }

            if (!invalidCredentials) {
                viewModel.saveContact(
                    firstName = binding.tietFirstName.text.toString().trim(),
                    lastName = binding.tietLastName.text.toString().trim(),
                    email = binding.tietEmail.text.toString().trim(),
                    phoneNumber = binding.tietPhoneNumber.text.toString().trim(),
                    address = binding.tietAddress.text.toString().trim()
                )
            }
        }
    }
}
