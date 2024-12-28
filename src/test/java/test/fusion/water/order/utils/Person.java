/**
 * Copyright (c) 2024 Araf Karsh Hamid
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 * <p>
 * or (per the licensee's choosing)
 * <p>
 * under the terms of the Apache 2 License version 2.0
 * as published by the Apache Software Foundation.
 */
package test.fusion.water.order.utils;

/**
 * ms-test-quickstart / Person 
 *
 * @author: Araf Karsh Hamid
 * @version: 0.1
 * @date: 2024-12-28T8:17 PM
 */
public class Person {

    private final String firstName;
    private final String lastName;
    private final String name;
    private final int age;
    private final String city;

    /**
     * Default Constructor
     */
    public Person() {
        this("Jane", "Doe", 29, "New York");
    }

    /**
     * Constructor
     * @param firstName
     * @param lastName
     * @param age
     * @param city
     */
    public Person(String firstName, String lastName, int age, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = firstName + " " + lastName;
        this.age = age;
        this.city = city;
    }

    /**
     * Returns Name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns Age
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns City
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns First Name
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns Last Name
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the String Representation of the Object
     * @return
     */
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }
}
