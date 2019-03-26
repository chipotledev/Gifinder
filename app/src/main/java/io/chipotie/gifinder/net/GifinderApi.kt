package io.chipotie.gifinder.net

import retrofit2.http.GET

/*
 * @author savirdev on 25/03/19
 */

interface GifinderApi{

    @GET
    fun trending()
}