package com.ang.acb.addressbook.presentation.addcontact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ang.acb.addressbook.R
import com.ang.acb.addressbook.databinding.FragmentAddContactBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactFragment : Fragment(R.layout.fragment_add_contact) {

    private val viewModel: AddContactViewModel by viewModels()

    private var _binding: FragmentAddContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
