package com.dumitrachecristian.petapp

import android.content.Context
import com.dumitrachecristian.petapp.utils.Utils
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UtilsUnitTest {

    private val contextMock = mockk<Context>()
    private val utils: Utils = Utils(contextMock)

    @Before
    fun setup() {
        every { contextMock.getString(R.string.cat) } returns "Cat"
        every { contextMock.getString(R.string.dog) } returns "Dog"
    }

    @Test
    fun test_getBackgroundImage() {
        Assert.assertEquals(R.drawable.catplaceholder, utils.getBackgroundImageBasedOnSpecies("Cat"))
        Assert.assertEquals(R.drawable.dogplaceholder, utils.getBackgroundImageBasedOnSpecies("Dog"))
        Assert.assertEquals(R.drawable.animalsplaceholder, utils.getBackgroundImageBasedOnSpecies("Rabbit"))
        Assert.assertEquals(R.drawable.animalsplaceholder, utils.getBackgroundImageBasedOnSpecies("Bird"))
    }
}