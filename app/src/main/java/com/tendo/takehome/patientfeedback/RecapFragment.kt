package com.tendo.takehome.patientfeedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.tendo.takehome.R
import com.tendo.takehome.databinding.FragmentRecapBinding
import com.tendo.takehome.patientfeedback.DoctorRating.Bad
import com.tendo.takehome.patientfeedback.DoctorRating.Good
import com.tendo.takehome.patientfeedback.DoctorRating.Mediocre
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.Loading
import com.tendo.takehome.patientfeedback.PatientFeedbackUiState.ReviewFeedback
import com.tendo.takehome.patientfeedback.UnderstoodDoctor.Maybe
import com.tendo.takehome.patientfeedback.UnderstoodDoctor.No
import com.tendo.takehome.patientfeedback.UnderstoodDoctor.Yes
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RecapFragment : Fragment() {
    private lateinit var binding: FragmentRecapBinding

    private val viewModel: PatientFeedbackViewModel by navGraphViewModels(R.id.client_feedback)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel.uiState.onEach {
            when(it){
                Loading -> isLoading(true)
                is ReviewFeedback -> showReviewFeedback(it.doctorName, it.rating, it.understandDiagnosis, it.diagnosisFeelings)
                else -> {}
            }
        }.launchIn(lifecycleScope)
    }

    private fun isLoading(isLoading: Boolean){
        with(binding){
            loadingCircle.isVisible = isLoading
            understandDiagnosis.isVisible = !isLoading
            feelingAboutDiagnosis.isVisible = !isLoading
            doctorRating.isVisible = !isLoading
            confirmText.isVisible  = !isLoading
            confirmButton.isVisible = !isLoading
        }
    }

    private fun showReviewFeedback(doctorName: String, rating: Int, understoodDiagnosis: String, diagnosisFeelings: String){
        isLoading(false)
        with(binding){
            understandDiagnosis.text = when(understoodDiagnosis.understandsDiagnosis()){
                Yes -> getString(R.string.understood_diagnosis, understoodDiagnosis)
                Maybe -> getString(R.string.unclear_diagnosis, understoodDiagnosis)
                No -> getString(R.string.didnt_understand_diagnosis, understoodDiagnosis)
            }
            feelingAboutDiagnosis.text = getString(R.string.about_your_feelings, diagnosisFeelings)
            doctorRating.text = when(rating.doctorRating()){
                Bad -> getString(R.string.hated_doctor, rating, doctorName)
                Mediocre -> getString(R.string.mediocre_doctor, rating, doctorName)
                Good -> getString(R.string.loved_doctor, rating, doctorName)
            }
        }
    }
}