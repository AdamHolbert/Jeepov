package com.gousslegend.deepov
import static org.junit.Assert.assertEquals

import org.junit.Test

import spock.lang.*

import com.gousslegend.deepov.Color
import com.gousslegend.deepov.Position
import com.gousslegend.deepov.board.MapBoard;

class WhenTestingColor extends spock.lang.Specification
{
	def "Testing opposite color"()
	{
		expect:
		color.getOppositeColor() == oppositeColor

		where:
		color | oppositeColor
		Color.WHITE | Color.BLACK
		Color.BLACK | Color.WHITE
	}
}