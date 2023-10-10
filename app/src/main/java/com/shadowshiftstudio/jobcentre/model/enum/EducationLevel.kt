package com.shadowshiftstudio.jobcentre.model.enum

enum class EducationLevel {
    PRIMARY, // НАЧАЛЬНОЕ ОБЩЕЕ ОБРАЗОВАНИЕ
    BASIC, // ОСНОВНОЕ ОБЩЕЕ ОБРАЗОВАНИЕ
    SECONDARY, // СРЕДНЕЕ ОБЩЕЕ ОБРАЗОВАНИЕ
    SECONDARY_PROFESSIONAL_QUALIFIED, // СРЕДНЕЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАНИЕ ПО ПРОГРАММАМ ПОДГОТОВКИ КВАЛИФИЦИРОВАННЫХ РАБОЧИХ (СЛУЖАЩИХ)
    SECONDARY_PROFESSIONAL_SPECIALIST, // СРЕДНЕЕ ПРОФЕССИОНАЛЬНОЕ ОБРАЗОВАНИЕ ПО ПРОГРАММАМ ПОДГОТОВКИ СПЕЦИАЛИСТОВ СРЕДНЕГО ЗВЕНА
    BACHELOR, // ВЫСШЕЕ ОБРАЗОВАНИЕ – БАКАЛАВРИАТ
    MASTER, // ВЫСШЕЕ ОБРАЗОВАНИЕ – СПЕЦИАЛИТЕТ ИЛИ МАГИСТРАТУРА
    POSTGRADUATE // ВЫСШЕЕ ОБРАЗОВАНИЕ – ПОДГОТОВКА КАДРОВ ВЫСШЕЙ КВАЛИФИКАЦИИ ПО ПРОГРАММАМ ПОДГОТОВКИ НАУЧНО-ПЕДАГОГИЧЕСКИХ КАДРОВ В АСПИРАНТУРЕ (АДЪЮНКТУРЕ), ПО ПРОГРАММАМ ОРДИНАТУРЫ, ПО ПРОГРАММАМ АССИСТЕНТУРЫ-СТАЖИРОВКИ
}

fun getRussianEducationLevel(educationLevel: EducationLevel): String {
    return when (educationLevel) {
        EducationLevel.PRIMARY -> "Начальное общее образование"
        EducationLevel.BASIC -> "Основное общее образование"
        EducationLevel.SECONDARY -> "Среднее общее образование"
        EducationLevel.SECONDARY_PROFESSIONAL_QUALIFIED -> "Среднее профессиональное образование"
        EducationLevel.SECONDARY_PROFESSIONAL_SPECIALIST -> "Среднее профессиональное образование"
        EducationLevel.BACHELOR -> "Бакалавриат"
        EducationLevel.MASTER -> "Магистратура"
        EducationLevel.POSTGRADUATE -> "Высшая квалификация"
    }
}