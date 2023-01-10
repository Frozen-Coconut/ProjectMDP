<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Api\UserController;
use App\Http\Controllers\Api\BloodRequestController;
use App\Http\Controllers\Api\BloodRequestUserController;
use App\Http\Controllers\Api\NotificationController;
use App\Models\BloodRequest;

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
    Route::get('/getBeforeDeadline', [BloodRequestController::class, 'GetAllBeforeDeadline']);
});

Route::prefix('bloodrequestsusers')->group(function() {
    Route::post('insert', [BloodRequestUserController::class, 'Insert']);
    Route::post('update', [BloodRequestUserController::class, 'Update']);
    Route::post('delete', [BloodRequestUserController::class, 'Delete']);
    Route::get('report', [BloodRequestUserController::class, 'GetReport']);
    Route::get('history', [BloodRequestUserController::class, 'GetHistory']);
});

Route::prefix('notifications')->group(function() {
    Route::post('insert', [NotificationController::class, 'Insert']);
    Route::post('update', [NotificationController::class, 'Update']);
    Route::post('delete', [NotificationController::class, 'Delete']);
    Route::get('get', [NotificationController::class, 'Get']);
    Route::get('/getAll', [NotificationController::class, 'GetAll']);
});
