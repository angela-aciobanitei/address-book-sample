package com.ang.acb.addressbook.presentation.contacts

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.ang.acb.addressbook.R
import com.ang.acb.addressbook.databinding.FragmentContactsBinding
import com.ang.acb.addressbook.presentation.utils.EventObserver
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsFragment : Fragment() {

    // See: https://developer.android.com/topic/libraries/view-binding#fragments
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var contactsAdapter: ContactsAdapter

    private val viewModel: ContactsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        setupRecyclerView()
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.contacts_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController()
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        contactsAdapter = ContactsAdapter { viewModel.onContactClick(it) }
        binding.rvContacts.adapter = contactsAdapter
    }

    private fun observeData() {
        viewModel.contacts.observe(viewLifecycleOwner, { contacts ->
            binding.noContactsHint.isVisible = contacts.isEmpty()
            contactsAdapter.submitList(contacts)
        })

        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = it
        })

        viewModel.message.observe(viewLifecycleOwner, EventObserver {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.navigation.observe(viewLifecycleOwner, EventObserver {
            val navHostFragment = requireActivity().supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController

            when (it) {
                is ContactsViewModel.Navigation.ToDetails -> {
                    navController.navigate(
                        ContactsFragmentDirections.actionContactsToContactDetails(it.id)
                    )
                }
            }
        })
    }
}
