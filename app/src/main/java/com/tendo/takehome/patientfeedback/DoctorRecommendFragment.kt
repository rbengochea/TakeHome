package com.tendo.takehome.patientfeedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tendo.takehome.R
import com.tendo.takehome.R.layout
import com.tendo.takehome.databinding.FragmentDoctorRecommendBinding
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.Loading
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.PatientRating
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.UnderstandDiagnosis
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DoctorRecommendFragment : Fragment() {
    private lateinit var binding:FragmentDoctorRecommendBinding

    private val viewModel: PatientFeedbackViewModel by navGraphViewModels(R.id.client_feedback)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoctorRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        //populate the list
        val adapter = ArrayAdapter(requireContext(), layout.list_item, listOf(1, 2, 3, 4,5,6,7,8,9,10))
        (binding.recommendValue.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        (binding.recommendValue.editText as? AutoCompleteTextView)?.setOnItemClickListener { _, view, _, _ ->
            viewModel.recordedRating((view as? TextView)?.text.toString().toInt())
        }


        viewModel.uiState.onEach{
            when(it){
                Loading -> isLoading(true)
                is PatientRating -> showRatingState(it.patientName, it.doctorName)
                is UnderstandDiagnosis -> findNavController().navigate(DoctorRecommendFragmentDirections.actionDoctorRecommendFragmentToDiagnosisFragment())
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun isLoading(isLoading: Boolean){
        with(binding){
            loadingCircle.isVisible = isLoading
            title.isVisible = !isLoading
            wouldRecommendText.isVisible = !isLoading
            recommendValue.isVisible = !isLoading

        }
    }

    private fun showRatingState(patientName: String?, doctorName: String){
        isLoading(false)
        with(binding){
            title.text = getString(R.string.hi_patient, patientName, doctorName)
            wouldRecommendText.text = getString(R.string.would_you_recommend, doctorName)
        }
    }

}