# INFO-6205 Final Project

## Introduction[![](./docs/img/pin.svg)](#introduction)

**MSD Radix Sort**. The radix sort takes in a sngle variable i.e. the file name, the array produced with the Chinese character names are converted to Pinyin English using the PinYin4j java library. (https://github.com/Negi97Mohit/INFO-6205-Final_Project/blob/main/src/main/java/edu/neu/coe/info6205/FinalProject/Sort/radixSort.java) .

---
## Table of contents[![](./docs/img/pin.svg)](#table-of-contents)
1. [File Structure](#File Structure)
2. [Chinese to PinYin English](#Chinese to PinYin English)
3. [Benchmarking](#Benchmarking)
4. [Reports](#Reports)
5. [Advance Radix Methods](#Advance Radix Methods)
    - [Multicast router](#mulitcast-router)
    - [Logging service](#logging-service)
    - [Development](#development)
6. [Benchmarking Results](#Benchmarking Results)
    - [MSD Radix Sort](#MSD Radix Sort)
    - [LSD Radix Sort](#LSD Radix Sort)
    - [Tim Sort](#Tim Sort)
    - [Dual pivot Quick Sort](#Dual pivot Quick Sort)
    - [Husky Sort](#Husky Sort)
    - [MSD Radix Exchange Sort](#MSD Radix Exchange Sort)

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