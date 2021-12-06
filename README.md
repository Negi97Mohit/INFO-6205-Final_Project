# INFO-6205 Final Project

## Introduction[![](./docs/img/pin.svg)](#introduction)

**MSD Radix Sort**. The radix sort takes in a sngle variable i.e. the file name, the array produced with the Chinese character names are converted to Pinyin English using the PinYin4j java library. ( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/tree/main/src/main/java/edu/neu/coe/info6205/FinalProject">Final Project File Link<a>) .

---
## Table of contents[![](./docs/img/pin.svg)](#table-of-contents){ Items are links to their file location }
1. [File Structure](#File Structure)
2. [Chinese to PinYin English](#Chinese to PinYin English)
3. [Sorting Algorithms](#Sorting Algorithms)
    - [MSD Radix Sort](#MSD Radix Sort)( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/blob/main/src/main/java/edu/neu/coe/info6205/FinalProject/Sort/RadixSortMSD.java">MSD Radix </a>)
    - [LSD Radix Sort](#LSD Radix Sort)( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/blob/main/src/main/java/edu/neu/coe/info6205/FinalProject/Sort/RadixSortLSD.java">LSD Radix </a>)
    - [Merge Sort](#Merge Sort)( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/blob/main/src/main/java/edu/neu/coe/info6205/FinalProject/Sort/Merge.java">Merge </a>)
    - [Tim Sort](#Tim Sort)( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/blob/main/src/main/java/edu/neu/coe/info6205/FinalProject/Sort/TimSort.java">Tim </a>)
    - [Dual-Pivot Quick Sort](#Dual-Pivot Quick Sort)( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/blob/main/src/main/java/edu/neu/coe/info6205/FinalProject/Sort/DualPivot.java">Dual-Pivot </a>)
    - [Husky Sort](#Husky Sort)( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/blob/main/src/main/java/edu/neu/coe/info6205/FinalProject/Sort/Husky.java">Husky</a>)
    - [3-Way Quick Radix Sort](#3-Way Quick Radix Sort)( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/blob/main/src/main/java/edu/neu/coe/info6205/FinalProject/Sort/ThreeWayRadix.java">3-way Quick Radix </a>)
4. [Benchmarking Results](#Benchmarking Results)( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/blob/main/src/main/java/edu/neu/coe/info6205/util/SortBenchmark.java">SortBenchmark.java </a>)
5. [Reports](#Reports)
   
---
## Software build[![](./docs/img/pin.svg)](#software-build)

_File Structure:_
<table>
  <tr>
    <td nowrap><strong>Final Project</strong></td>
    <td>Main Project File ( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/tree/main/src/main/java/edu/neu/coe/info6205/FinalProject">Final Project </a>).</td>
  </tr>
  <tr>
    <td nowrap><strong>Sort</strong></td>
    <td>List of sorting algorithms( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/tree/main/src/main/java/edu/neu/coe/info6205/FinalProject/Sort">Sort </a>).</td>
  </tr>
  <tr>
    <td nowrap><strong>Utils</strong></td>
    <td>List of Utils files ( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/tree/main/src/main/java/edu/neu/coe/info6205/FinalProject/Utils">Utils </a>) .</td>
  </tr>
  <tr>
    <td nowrap><strong>benchmarking</strong></td>
    <td>SortBenchmark.java ( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/blob/main/src/main/java/edu/neu/coe/info6205/util/SortBenchmark.java">SortBenchmark </a>) .</td>
  </tr>
  <tr>
    <td nowrap><strong>Test Cases</strong></td>
    <td>List of all test files for sorting algorithms( <a href="https://github.com/Negi97Mohit/INFO-6205-Final_Project/tree/main/src/test/java/edu/neu/coe/info6205/FinalProject">Test Cases </a>) .</td>
  </tr>
</table>

## Chinese to PinYin English[![](./docs/img/pin.svg)](#software-build)

```java
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

```