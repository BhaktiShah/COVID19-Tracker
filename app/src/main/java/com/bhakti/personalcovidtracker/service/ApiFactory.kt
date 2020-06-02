package com.bhakti.personalcovidtracker.service

object ApiFactory {

    fun provideCovid19Api() = NovelCoronaApiClient(RetrofitFactory.provideRetrofit())
}