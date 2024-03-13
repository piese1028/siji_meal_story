package io.github.apwlq.schoolmealinfo

/*
 * @author Bruce0203
 * @license MIT License
 */

import java.awt.Color
import java.awt.Font
import java.awt.GraphicsEnvironment
import java.io.File
import java.util.Properties
import javax.imageio.ImageIO


object AddTextToImgByStory {
    @JvmStatic
    fun execute(file: File, meal: String, out: File, date: String) {
        //read the image
        val image = ImageIO.read(file)
        //get the Graphics object
        val g = image.graphics
        //set font
        val prop = Properties()
        prop.load(File("assets/config/story.properties").bufferedReader())
        g.color = hex2Rgb(prop["draw-color"].toString())
        g.font = getFont().deriveFont(prop["draw-font-size"].toString().toFloat())
        meal.split("\n").forEachIndexed { ind, str ->
            g.drawString(str,
                prop["meal-x"].toString().toInt(),
                prop["meal-y"].toString().toInt()
                        + ind * prop["draw-leading"].toString().toInt())
        }
        g.font = getFont().deriveFont(prop["date-font-size"].toString().toFloat())
        date.split("\n").forEachIndexed { ind, str ->
            g.drawString(str,
                prop["date-x"].toString().toInt(),
                prop["date-y"].toString().toInt()
                        + ind * prop["date-leading"].toString().toInt())
        }
        g.dispose()
        //write the image
        out.mkdirs()
        ImageIO.write(image, "png", out)
    }

    fun getFont(): Font {
        val font: Font = Font.createFont(Font.TRUETYPE_FONT, File("assets/font/story.ttf"))
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
        ge.registerFont(font)
        return font
    }

    @JvmStatic
    fun hex2Rgb(colorStr: String): Color {
        var s = colorStr
        if (!s.startsWith("#")) s = "#$colorStr"
        return Color(
            Integer.parseInt(s.substring(1, 3), 16),
            Integer.parseInt(s.substring(3, 5), 16),
            Integer.parseInt(s.substring(5, 7), 16)
        )
    }

}
