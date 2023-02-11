package com.example.naveenchallenge

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Configuration
import com.codeborne.selenide.Selenide.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import org.openqa.selenium.By


class MainPageTest {
    companion object {
        @BeforeAll
        fun setUpAll() {
            Configuration.browserSize = "1280x800"
        }
    }

    @BeforeEach
    fun setUp() {
        open("https://petdiseasealerts.org/forecast-map")
        sleep(3000)
    }

    @Test
    fun search() {
        switchTo().frame(element(".capc-map-embed iframe"))

        var elements = element("#regions")
        var regions = elements.findElements(By.className("region"))
        var regionDetailBackButton = element("#map-component > ul > li:nth-child(1) > a")

        regions
            .filter { it.getAttribute("id") != "district-of-columbia"} // I could not handle this one
            .forEach {
            val state = States.findById(it.getAttribute("id"))

            actions()
                .moveToElement(element("#${state.id.toLowerCase()}"), state.xOffset, state.yOffset)
                .click()
                .perform()

            val stateName = element("#map-component > ul > li:nth-child(2) > span")
            stateName.shouldBe(visible)

            assertEquals(state.id.toFormalName(), stateName.innerText())
            regionDetailBackButton.click()
            sleep(1000)
        }
    }
}

fun String.toFormalName(): String {
    return this
        .lowercase()
        .replace("-", " ")
        .split(' ')
        .joinToString(" ") { it.replaceFirstChar(Char::uppercaseChar) }
}

enum class States(val id: String, val xOffset: Int, val yOffset: Int) {
    ALABAMA("ALABAMA", 0, 0),
    ALASKA("ALASKA", 0, 0),
    ARIZONA("ARIZONA", 0, 0),
    ARKANSAS("ARKANSAS", 0, 0),
    CALIFORNIA("CALIFORNIA", 0, 0),
    COLORADO("COLORADO", 0, 0),
    CONNECTICUT("CONNECTICUT", 0, 0),
    DELAWARE("DELAWARE", 0, 0),
    DISTRICT_OF_COLUMBIA("DISTRICT-OF-COLUMBIA", 0, 0),
    FLORIDA("FLORIDA", 30, 10),
    GEORGIA("GEORGIA", 0, 0),
    HAWAII("HAWAII", 40, 25),
    IDAHO("IDAHO", 0, 0),
    ILLINOIS("ILLINOIS", 0, 0),
    INDIANA("INDIANA", 0, 0),
    IOWA("IOWA", 0, 0),
    KANSAS("KANSAS", 0, 0),
    KENTUCKY("KENTUCKY", 0, 0),
    LOUISIANA("LOUISIANA", -10, 0),
    MAINE("MAINE", 0, 0),
    MARYLAND("MARYLAND", 0, 0),
    MASSACHUSETTS("MASSACHUSETTS", 0, 0),
    MICHIGAN("MICHIGAN", 30, 0),
    MINNESOTA("MINNESOTA", 0, 0),
    MISSISSIPPI("MISSISSIPPI", 0, 0),
    MISSOURI("MISSOURI", 0, 0),
    MONTANA("MONTANA", 0, 0),
    NEBRASKA("NEBRASKA", 0, 0),
    NEVADA("NEVADA", 0, 0),
    NEW_HAMPSHIRE("NEW-HAMPSHIRE", 0, 0),
    NEW_JERSEY("NEW-JERSEY", 10, 0),
    NEW_MEXICO("NEW-MEXICO", 0, 0),
    NEW_YORK("NEW-YORK", 0, 0),
    NORTH_CAROLINA("NORTH-CAROLINA", 0, 0),
    NORTH_DAKOTA("NORTH-DAKOTA", 0, 0),
    OHIO("OHIO", 0, 0),
    OKLAHOMA("OKLAHOMA", 0, 0),
    OREGON("OREGON", 0, 0),
    PENNSYLVANIA("PENNSYLVANIA", 0, 0),
    RHODE_ISLAND("RHODE-ISLAND", 0, 0),
    SOUTH_CAROLINA("SOUTH-CAROLINA", 0, 0),
    SOUTH_DAKOTA("SOUTH-DAKOTA", 0, 0),
    TENNESSEE("TENNESSEE", 0, 0),
    TEXAS("TEXAS", 0, 0),
    UTAH("UTAH", 0, 0),
    VERMONT("VERMONT", 0, 0),
    VIRGINIA("VIRGINIA", 0, 0),
    WASHINGTON("WASHINGTON", 0, 0),
    WEST_VIRGINIA("WEST-VIRGINIA", 0, 0),
    WISCONSIN("WISCONSIN", 0, 0),
    WYOMING("WYOMING", 0, 0)
    ;

    companion object {
        fun findById(id: String): States {
            return States.values().find { it.id == id.toUpperCase() }!!
        }
    }
}
