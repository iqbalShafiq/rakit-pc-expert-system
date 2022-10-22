package space.iqbalsyafiq.rakitpc.util

import android.content.Context
import space.iqbalsyafiq.rakitpc.model.Question
import space.iqbalsyafiq.rakitpc.model.Rule
import space.iqbalsyafiq.rakitpc.model.network.DataResponse
import java.io.IOException

object DataUtil {

    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun convertDataToQuestions(data: DataResponse): List<Question> {
        val questions = mutableListOf<Question>()

        data.values.forEach { value ->
            questions.add(
                Question(
                    id = value[0],
                    question = value[1],
                    yes = value[2],
                    no = value[3],
                    q = value[4],
                    qn = value[5]
                )
            )
        }

        return questions
    }

    fun convertDataToRules(data: DataResponse): List<Rule> {
        val rules = mutableListOf<Rule>()

        data.values.forEach { value ->
            rules.add(
                Rule(
                    rules = value[0],
                    output = value[1],
                    motherboard = value[2],
                    cpu = value[3],
                    ram = value[4],
                    storage = value[5],
                    powerSupply = value[6],
                    gpu = value[7],
                    coolerCPU = value[8],
                    casing = value[9],
                    totalPrice = value[10],
                    description = value[11]
                )
            )
        }

        return rules
    }
}