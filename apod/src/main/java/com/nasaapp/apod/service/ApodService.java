package com.nasaapp.apod.service;

import com.nasaapp.apod.entity.Apod;

public interface ApodService {

	Apod getApods();

	Apod getApodByDate(String date);

}
