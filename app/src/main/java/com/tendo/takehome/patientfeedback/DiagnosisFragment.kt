package com.tendo.takehome.patientfeedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.tendo.takehome.R
import com.tendo.takehome.databinding.FragmentDiagnosisBinding
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.DiagnosisFeelings
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.Loading
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.ReviewFeedback
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.UnderstandDiagnosis
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DiagnosisFragment : Fragment() {
    private lateinit var binding: FragmentDiagnosisBinding

    private val viewModel: PatientFeedbackViewModel by navGraphViewModels(R.id.client_feedback)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiagnosisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.saveButton.setOnClickListener { viewModel.doneButtonClicked(binding.patientResponse.editText?.text?.toString()) }
        viewModel.uiState.onEach {
            when(it) {
                Loading -> isLoading(true)
                is UnderstandDiagnosis -> showUnderstandDiagnosis(it.diagnosis, it.doctorName)
                is DiagnosisFeelings -> showDiagnosisFeelings(it.diagnosis)
                is ReviewFeedback -> findNavController().navigate(DiagnosisFragmentDirections.actionDiagnosisFragmentToRecapFragment())
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun isLoading(isLoading: Boolean){
        with(binding){
            loadingCircle.isVisible = isLoading
            questionText.isVisible = !isLoading
            patientResponse.isVisible = !isLoading
            saveButton.isVisible = !isLoading
        }
    }

    private fun showUnderstandDiagnosis(diagnosis: String?, doctorName: String){
        isLoading(false)
        binding.questionText.text = getString(R.string.diagnosis, diagnosis, doctorName)
        binding.patientResponse.editText?.text?.clear() //clear string when showing new prompt
    }

    private fun showDiagnosisFeelings(diagnosis: String?){
        isLoading(false)
        binding.questionText.text = getString(R.string.feedback, diagnosis)
        binding.patientResponse.editText?.text?.clear() //clear string when showing new prompt
    }
}