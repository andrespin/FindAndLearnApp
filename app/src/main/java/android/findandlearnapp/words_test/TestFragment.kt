package android.findandlearnapp.words_test

import android.findandlearnapp.App
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.findandlearnapp.databinding.FragmentTestBinding
import android.findandlearnapp.words_manager.WordsManagerViewModel
import androidx.lifecycle.ViewModelProvider
import java.io.*


// https://www.blueappsoftware.com/how-to-read-text-file-in-android-tutorial/

// https://www.geeksforgeeks.org/how-to-read-a-text-file-in-android/

class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding

    private lateinit var viewModel: TestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentTestBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TestViewModel::class.java).apply {
            App.instance.appComponent.inject(this)
        }

//        val nounsList = convertToList(readTxtFile("ru_nouns.txt"))
//        val verbsList = convertToList(readTxtFile("ru_verbs.txt"))
//        val adverbsList = convertToList(readTxtFile("ru_adverbs.txt"))
//        val adjectivesList = convertToList(readTxtFile("ru_adjectives.txt"))
//
//        println("nounsList : size ${nounsList.size}, $nounsList")
//        println("verbsList : size ${verbsList.size}, $verbsList")
//        println("adverbsList : size ${adverbsList.size}, $adverbsList")
//        println("adjectivesList : size ${adjectivesList.size}, $adjectivesList")

//        val test = convertToList("ещё, пространство, вой")
//        val test = convertToListTest("вой  ")
//        println("test convert: size ${test.size}, $test")

    }

    private fun readTxtFile(filename: String): String {
        var text = ""
        try {
            val sampleText: String =
                requireActivity()
                    .assets
                    .open(filename)
                    .bufferedReader().use {
                        it.readText()
                    }
            text = sampleText
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return text + " "
    }

    private fun convertToList(text: String): List<String> {
        val list = arrayListOf<String>()

        val array = text.toCharArray()

        var word = ""

        for (i in 0 until array.size) {

            if (array[i] in 'а'..'я' || array[i] == 'й' || array[i] == 'ё') {
                word += array[i]
            } else {
                if (word != "") {
                    list.add(word)
                }
                word = ""
            }
        }
        return list
    }

    private fun convertToListTest(text: String): List<String> {
        val list = arrayListOf<String>()

        val array = text.toCharArray()

        println("array size = ${array.size}")

        var word = ""

        for (i in 0 until array.size) {

            //   println("array[i] ${array[i]}, array[i] == 'й' : ${array[i] == 'й'} ")

            if (array[i] in 'а'..'я' || array[i] == 'й' || array[i] == 'ё') {
                println("array[i] ${array[i]} , true")
                word += array[i]
            } else {
                if (word != "") {
                    list.add(word)
                }
                println("array[i] ${array[i]} , false")
                word = ""
            }

        }
        return list
    }






    companion object {
        fun newInstance() =
            TestFragment()
    }
}