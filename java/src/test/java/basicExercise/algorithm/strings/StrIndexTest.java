package basicExercise.algorithm.strings;

import junit.framework.TestCase;

public class StrIndexTest extends TestCase {

    public void testSortedIndex() {
        assertEquals("1324",new StrIndex().SortedIndex("acbe"));
        assertEquals("11324",new StrIndex().SortedIndex("aacbe"));

    }
}