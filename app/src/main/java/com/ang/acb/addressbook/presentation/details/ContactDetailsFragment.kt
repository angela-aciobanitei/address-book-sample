package com.ang.acb.addressbook.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ang.acb.addressbook.databinding.FragmentContactDetailsBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailsFragment : Fragment() {

    private val viewModel: ContactDetailsViewModel by viewModels()
    private val navArgs: ContactDetailsFragmentArgs by navArgs()

    private var _binding: FragmentContactDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getContact(navArgs.contactId)
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeData() {
        viewModel.contact.observe(viewLifecycleOwner, {
            binding.tvName.text = "${it.firstName} ${it.lastName}"
            binding.tvEmail.text = it.email
            binding.tvPhoneNumber.text = it.phoneNumber
            binding.tvAddress.text = it.address
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = it
        })

        viewModel.message.observe(viewLifecycleOwner, {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })
    }
}
