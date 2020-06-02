package com.bhakti.personalcovidtracker.service

abstract class ApiClient {

    val APPLICATION_JSON = "application/json"
    val HTTP_STATUS_OK = 200
    val HTTP_STATUS_FORBIDDEN = 403
    val HTTP_STATUS_NOT_FOUND = 404
    val HTTP_STATUS_RETRY_BLOCKED = 429

}