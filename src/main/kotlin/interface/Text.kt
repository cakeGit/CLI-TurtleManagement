package `interface`

import CLIApp
import kotlin.math.*

class Text {
    companion object {
        fun enforceWidth(string: String, intendedWidth: Int): String {
            if (string.length > intendedWidth) {
                val scrollLength = string.length;
                val center: Double = scrollLength / 2.0;
                val scroll: Double = (CLIApp.TICK_HOLDER / 10.0) % scrollLength;

                //4\frac{\operatorname{abs}\left(t-c\right)}{l}-0.5

                var offset = (4 * (abs(scroll - center) / scrollLength) - 0.5)
                offset = min(max(offset, 0.0), 1.0)

                val start: Int = (offset * (scrollLength - intendedWidth)).roundToInt();
                return string.substring(start, start + intendedWidth)
            }

            val padding = intendedWidth - string.length;

            val paddingLeft: Int = floor(padding / 2f).toInt();
            val paddingRight: Int = ceil(padding / 2f).toInt();

            return " ".repeat(paddingLeft) + string + " ".repeat(paddingRight);
        }

        fun wrapString(string: String, intendedWidth: Int): List<String> {

            val lines = mutableListOf("");
            if (string == "") return lines;

            val remainingWords = string.split(" ").toMutableList();

            while (remainingWords.size != 0) {
                val currentLine = lines.last();
                val remainingWord = remainingWords.first();
                val remainingWidth = intendedWidth - currentLine.length;

                //Hard wrap
                if (remainingWord.length > intendedWidth) {
                    //Newline if less than 10% of the width remains
                    if (remainingWidth < intendedWidth * 0.1) {
                        lines.add(remainingWord.substring(0, intendedWidth))
                        remainingWords[0] = remainingWord.substring(intendedWidth)
                    } else {
                        lines[lines.lastIndex] += " " + remainingWord.substring(0, remainingWidth-1);
                        remainingWords[0] = remainingWord.substring(remainingWidth-1)
                    }
                }
                //Soft wrap
                if (currentLine.length + 1 + remainingWord.length > intendedWidth) {
                    lines.add(remainingWord);
                    remainingWords.removeFirst();
                } else {
                    lines[lines.lastIndex] += " $remainingWord";
                    remainingWords.removeFirst();
                }
            }

            return lines.map { line -> line.strip() };
        }
    }

}