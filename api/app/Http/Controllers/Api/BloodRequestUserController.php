<?php

namespace App\Http\Controllers\Api;

use App\Models\User;
use Illuminate\Http\Request;
use App\Models\BloodRequestUser;
use App\Http\Controllers\Controller;

class BloodRequestUserController extends Controller
{
    public function Insert(Request $request)
    {
        $user = User::where('email','=',$request->email)->first();
        if ($user->accept()->where('blood_request_id','=',$request->blood_request_id)->exists()) {
            return response("Request sudah pernah diterima!");
        }
        else {
            BloodRequestUser::create([
                "user_id" => $user->id,
                "blood_request_id" => $request->blood_request_id,
                "status" => 0
            ]);
        }
        return response("Request berhasil diterima!");
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
