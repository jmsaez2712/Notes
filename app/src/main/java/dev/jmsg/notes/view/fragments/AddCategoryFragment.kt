package dev.jmsg.notes.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import dev.jmsg.notes.R
import dev.jmsg.notes.model.entity.Category
import dev.jmsg.notes.viewmodel.CategoryViewModel


class AddCategoryFragment : Fragment() {

    lateinit var toolbar: Toolbar
    lateinit var nameTextField: EditText
    lateinit var cvm: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents(view)
        setNavigation(view)
    }

    fun initComponents(view: View)
    {
        toolbar = view.findViewById(R.id.toolbarCategory)
        nameTextField = view.findViewById(R.id.etTitleCategory)
        cvm = ViewModelProvider(requireActivity()).get(CategoryViewModel::class.java)
    }

    private fun setNavigation(view : View){
        var navController = Navigation.findNavController(view)
        var appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        toolbar.inflateMenu(R.menu.note_add_menu)
        toolbar.setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
        NavigationUI.setupWithNavController(toolbar, navController,appBarConfiguration)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item.itemId

        when(id){
            R.id.add_note-> createCategory()
        }
        return super.onOptionsItemSelected(item)
    }

    fun createCategory() {
        if (!nameTextField.text.isEmpty()) {
            var category = Category(null, nameTextField.text.toString())
            cvm.insertCategory(category)
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

}