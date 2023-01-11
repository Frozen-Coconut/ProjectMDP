<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;

class UserController extends Controller
{
    public function Insert(Request $request)
    {
        $user = User::create($request->all());
        return response()->json($user);
    }

    public function Update(Request $request)
    {
        if ($request->has('id')) {
            $user = User::where('id', $request->id)->first()->update($request->all());
        } else {
            $user = User::where('email', $request->email)->first()->update($request->all());
        }
        return response()->json($user);
    }

    public function Delete(Request $request)
    {
        if ($request->has('id')) {
            $user = User::where('id', $request->id)->first()->delete();
        } else {
            $user = User::where('email', $request->email)->first()->delete();
        }
        return response()->json($user);
    }

    public function Get(Request $request)
    {
	if ($request->has('id')) {
            $user = User::where('id', $request->id)->first();
        } else {
            $user = User::where('email', $request->email)->first();
        }
        return response()->json($user);
    }

    public function GetAll(Request $request)
    {
        if ($request->has('type')) {
            if ($request->type == 'user') {
                $users = User::whereNotNull('date_of_birth')->get();
                return response()->json($users);
            } else if ($request->type == 'puskesmas') {
                $users = User::whereNull('date_of_birth')->get();
                return response()->json($users);
            }
        }
        $users = User::all();
        return response()->json($users);
    }

    public function BanUser(Request $request) {

        $status = $request->status == "1" ? 1 : 0;

        $user = User::where('email', $request->email)->update([
            "status" => $status
        ]);

        return response()->json($user);
    }

    public function GetUsers(Request $request) {
        $users = User::whereNotNull('date_of_birth')->get();

        return response()->json($users);
    }

    public function GetPuskesmas(Request $request) {
        $users = User::whereNotNull('phone')->get();

        return response()->json($users);
    }
}
