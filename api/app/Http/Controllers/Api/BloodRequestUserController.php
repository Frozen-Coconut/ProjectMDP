<?php

namespace App\Http\Controllers;

use App\Models\BloodRequestUser;
use Illuminate\Http\Request;

class BloodRequestUserController extends Controller
{
    public function Insert(Request $request)
    {
        $blood_request_user = BloodRequestUser::create($request->all());
        return response()->json($blood_request_user);
    }

    public function Update(Request $request)
    {
        $blood_request_user = BloodRequestUser::where('id', $request->id)->first()->update($request->all());
        return response()->json($blood_request_user);
    }

    public function Delete(Request $request)
    {
        $blood_request_user = BloodRequestUser::where('id', $request->id)->first()->delete();
        return response()->json($blood_request_user);
    }

    public function Get(Request $request)
    {
        $blood_request_user = BloodRequestUser::where('id', $request->id)->first();
        return response()->json($blood_request_user);
    }

    public function GetUser(Request $request)
    {
        $blood_request_user = BloodRequestUser::where('user_id', $request->id)->get();
        return response()->json($blood_request_user);
    }
}
