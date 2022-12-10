<?php

use App\Http\Controllers\Api\BloodRequestController;
use App\Http\Controllers\Api\UserController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::prefix('users')->group(function () {
    Route::post('insert', [UserController::class, 'Insert']);
    Route::post('update', [UserController::class, 'Update']);
    Route::post('delete', [UserController::class, 'Delete']);
    Route::get('get', [UserController::class, 'Get']);
    Route::get('/', [UserController::class, 'GetAll']);
});

Route::prefix('bloodrequests')->group(function () {
    Route::post('insert', [BloodRequestController::class, 'Insert']);
    Route::post('update', [BloodRequestController::class, 'Update']);
    Route::post('delete', [BloodRequestController::class, 'Delete']);
    Route::get('get', [BloodRequestController::class, 'Get']);
    Route::get('/', [BloodRequestController::class, 'GetAll']);
});
