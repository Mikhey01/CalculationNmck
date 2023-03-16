package ru.rezvantsev.myapplication.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class DataNMCK(
    val id: Long,
    val nameOrganization: String,
    val nameAuthor: String,
    val numberGuardsDuty: String,                       //Количество охранников на посту
    // val numberHoursPostSecurity: String,                //Количество часов охраны поста
    // val numberDaysPostSecurity: String,                     //Количество дней охраны поста
    //   val nightShifts: Boolean = false,                //Ночные смены (Да/Нет)
    //  val numberSecurityPosts: String,                       //Количество требуемых постов охраны по контракту
    //   val mrot: String,                                   //МРОТ
    // val consumerPriceIndex: Double,                      //Индекс потребительских цен
    // val nds: Double = 0.2,                           // Налог на добавленную стоимость
    // val insurancePremiumRrate: Double = 0.302,       // Ставка страховых взносов
//    val correctionFactorUd1: Boolean = false,           //дополнительный коэффициент (наличие спецсредств у охранника) да = 0,05; нет = 0
//    val correctionFactorUd2: Boolean = false,           //дополнительный коэффициент (место проведения массовых мероприятий) да = 0,3; нет = 0
//    val correctionFactorUd3: Boolean = false,          //дополнительный коэффициент (объект с требованиями по антитеррористической защищенности) да = 0,1; нет = 0
    //  val numberHolidaysWeekends: String
   val content: List<StepNMCK>?
) : Parcelable
