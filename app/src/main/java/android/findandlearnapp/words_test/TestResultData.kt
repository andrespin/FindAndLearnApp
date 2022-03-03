package android.findandlearnapp.words_test

import android.os.Parcel
import android.os.Parcelable

data class TestResultData(
    val areAllAnswersCorrect: Boolean,
    val totalAnswers: Int,
    val rightAnsers: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (areAllAnswersCorrect) 1 else 0)
        parcel.writeInt(totalAnswers)
        parcel.writeInt(rightAnsers)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestResultData> {
        override fun createFromParcel(parcel: Parcel): TestResultData {
            return TestResultData(parcel)
        }

        override fun newArray(size: Int): Array<TestResultData?> {
            return arrayOfNulls(size)
        }
    }
}
