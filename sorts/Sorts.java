package sorts;

public class Sorts {

	/**
	 * Interface for objects that determine a positive integer for objects of a
	 * given type so that those objects may be sorted by those integers.
	 */
	public static interface ToInteger<T> {
		/**
		 * What integer should we use for sorting purposes for the given item?
		 */
		int v(T item);
	}

	/**
	 * Sort the given array using counting sort.
	 * 
	 * @param array
	 *            The array (or anything) to sort, assumed non-null and with no null
	 *            elements.
	 * @param toInt
	 *            A means of determining a number (for sorting purposes) for the
	 *            items in the array
	 */
	public static <T> void countingSort(T[] array, ToInteger<T> toInt) {

		// The maximum value in the array (once we calculate it)
		int maxVal = 0;
		// A. Find the maximum value in the array
		// add loop here
		for (int i = 0; i < array.length; i++) {
			if (toInt.v(array[i]) > maxVal)
				maxVal = toInt.v(array[i]);
		}
		// The occurrences of each value (once we calculate them)
		int[] counts = new int[maxVal + 1];
		// B. tabulate the occurrences of each value
		// add loop here
		for (int i = 0; i < array.length; i++) {
			int temp = toInt.v(array[i]);
			counts[temp]++;
		}

		// The initial places for each value (once we calculate them)
		int[] nextPlace = new int[maxVal + 1];
		// C. Determine the initial next place for each value
		nextPlace[0] = 0;

		// sumPrev is the sum of all previous counts so the nextPlace
		// knows where its current nextPlace[i] starts
		int sumPrev = counts[0];

		for (int i = 1; i < counts.length; i++) {
			if (counts[i] != 0) {
				// set the current index to the sum of all previous index
				nextPlace[i] = sumPrev;

				// add the new count to sumPrev
				sumPrev += counts[i];
			}
		}

		

		// The auxiliary array into which to sort the array
		@SuppressWarnings("unchecked")
		T[] aux = (T[]) new Object[array.length];
		// D. Sort the items into aux

		// run through every element in array
		for(int i = 0; i < array.length; i++){
			// grab the key of the element at the current index
			int el = toInt.v(array[i]);
			// make sure this key is in nextPlace
			if (el < nextPlace.length) {
				// put the element at array[i] in the correct spot in aux
				aux[nextPlace[el]] = array[i];
				// update nextPlace[el] so the next value with this key goes in the next spot
				nextPlace[el]++;
			}
		}
		
		// copy the items from aux back into array
		for(int i = 0; i < array.length; i++) {
			array[i] = aux[i];
		}
		
	}
	// E. move them back to array
	// add loop here

	/**
	 * Sort the given array using radix sort with the given radix.
	 * 
	 * @param array
	 *            The array to sort
	 * @param r
	 *            The radix to use (must be greater than 1)
	 */
	public static void radixSort(Integer[] array, final int r) {
		assert r > 1;

		// The maximum number of digits for any number in array
		// (once we calculate it)
		int maxNumDigits = 0;

		// calculate the number of digits
		for (int i = 0; i < array.length; i++) {
			int numDigits = (int) Math.ceil(Math.log(array[i]) / Math.log(r));
			if (numDigits > maxNumDigits)
				maxNumDigits = numDigits;
		}

		// sort by each digit
		int rPow = 1;
		for (int i = 0; i < maxNumDigits; i++) {
			// "Final" version of rPow that we can use inside an anonymous inner class
			final int rp = rPow;
			countingSort(array, new ToInteger<Integer>() {
				public int v(Integer item) {
					throw new UnsupportedOperationException();
				}
			});
			rPow *= r;
		}
	}

}
