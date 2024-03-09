# Java Tricks and Handy Functions for DSA

## Taking Input
**Scanner for taking input**:
```java
  Scanner scanner = new Scanner(System.in);
  int intValue = scanner.nextInt();
  double doubleValue = scanner.nextDouble();
  String stringValue = scanner.nextLine();
  scanner.close();
```

## Collections Framework Utilities

**Sorting a list**:

`Collections.sort(list);`

**Binary search**:

`Collections.binarySearch(list, key);`

**Reversing a list**:

`Collections.reverse(list);`

**Finding max/min in a collection**:

`Collections.max(collection);` and `Collections.min(collection);`

**Swapping elements in a list**:

`Collections.swap(list, i, j);`

**Filling a list with a value**:

`Collections.fill(list, value);`

Creating an immutable list:

`List<String> list = List.of("a", "b", "c");`

Creating an immutable set:

`Set<String> set = Set.of("a", "b", "c");`

Creating an immutable map:

`Map<String, Integer> map = Map.of("a", 1, "b", 2, "c", 3);`

Converting a set to a list:

`new ArrayList<>(set);`

Checking if a collection is empty:

`collection.isEmpty();`

Getting the size of a collection:

`collection.size();`

Clearing a collection:

`collection.clear();`

## Array Utilities

- **Sorting an array**:

`Arrays.sort(arr);`

- **Converting a list to an array**:

`list.toArray(new Type[list.size()]);`

- **Converting an array to a list**:

`Arrays.asList(arr);`

- **Copying an array**:

`Arrays.copyOf(arr, newLength);`

- **Filling an array with a value**:

`Arrays.fill(arr, value);`

- **Binary search in a sorted array**:

`Arrays.binarySearch(arr, key);`

- Creating a stream from an array:

`Arrays.stream(arr);`

- Comparing arrays for equality:

`Arrays.equals(arr1, arr2);`

- Finding the index of an element in an array:

`Arrays.asList(arr).indexOf(element);`

- Concatenating arrays:

`int[] concatenatedArray = IntStream.concat(Arrays.stream(arr1), Arrays.stream(arr2)).toArray();`

## String Manipulations

- **Joining strings with a delimiter**: `String.join(delimiter, elements);`
- **Splitting a string into an array**: `str.split(regex);`
- **Converting to upper/lower case**: `str.toUpperCase();` and `str.toLowerCase();`
- **Replacing characters**: `str.replace(oldChar, newChar);`
- **Checking if a string contains a substring**: `str.contains(substring);`
- **Checking if a string starts or ends with a substring**: `str.startsWith(prefix); and str.endsWith(suffix);`
- **Removing whitespace from the beginning and end of a string**: `str.trim();`
- **Converting a character array to a string**: `String.valueOf(charArray);`
- **Finding the index of a character or substring**: `str.indexOf(charOrSubstring);`

## Math Utilities

- **Finding max/min of two numbers**: `Math.max(a, b);` and `Math.min(a, b);`
- **Exponentiation**: `Math.pow(base, exponent);`
- **Square root**: `Math.sqrt(number);`
- **Absolute value**: `Math.abs(number);`
- **Rounding numbers**: `Math.round(number);`
- **Generating random integers in a range**: int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
- **Calculating the logarithm**: Math.log(number); (natural logarithm), Math.log10(number); (base 10)
- **Trigonometric functions**: Math.sin(angle);, Math.cos(angle);, Math.tan(angle);
- **Raising e to the power of a number**: Math.exp(number);
- **Rounding to a specific number of decimal places**:

`double roundedNumber = Math.round(number * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);`

## Stream API for Collections

- **Filtering a collection**: `list.stream().filter(predicate).collect(Collectors.toList());`
- **Mapping elements**: `list.stream().map(function).collect(Collectors.toList());`
- **Reducing to a single value**: `list.stream().reduce(initialValue, accumulator);`
- **Sorting**: `list.stream().sorted().collect(Collectors.toList());`
- **Finding max/min**: `list.stream().max(comparator);` and `list.stream().min(comparator);`

## Miscellaneous

- **Generating random numbers**: `Random rand = new Random(); int randomNum = rand.nextInt(upperBound);`
- **Checking if a string is a number**: `str.matches("-?\\d+(\\.\\d+)?");`
- **Converting between data types**: `Integer.parseInt(str);`, `String.valueOf(number);`
