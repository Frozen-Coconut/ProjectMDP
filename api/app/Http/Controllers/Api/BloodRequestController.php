<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\BloodRequest;
use Illuminate\Http\Request;

class BloodRequestController extends Controller
{
    public function Insert(Request $request)
    {
        $blood_request = BloodRequest::create($request->all());
        return response()->json($blood_request);
    }

    public function Update(Request $request)
    {
        $blood_request = BloodRequest::where('id', $request->id)->first()->update($request->all());
        return response()->json($blood_request);
    }

    public function Delete(Request $request)
    {
        $blood_request = BloodRequest::where('id', $request->id)->first()->delete();
        return response()->json($blood_request);
    }

    public function Get(Request $request)
    {
        $blood_request = BloodRequest::where('id', $request->id)->first();
        return response()->json($blood_request);
    }

    public function GetAll(Request $request)
    {
        $blood_requests = BloodRequest::all();
        return response()->json($blood_requests);
    }
}
