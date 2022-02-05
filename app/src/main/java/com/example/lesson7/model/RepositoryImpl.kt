package com.example.lesson7.model

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {

    override fun getWeatherDetailsFromServer( lat: Double,
                                              lon: Double,
                                              callback: retrofit2.Callback<WeatherDTO> )
    {
        remoteDataSource.getWeatherDetails(lat, lon, callback)
    }
}