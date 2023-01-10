<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Notification;
use App\Models\User;
use Illuminate\Http\Request;

class NotificationController extends Controller
{
    public function Insert(Request $request)
    {
        $user = User::where('email', $request->email)->first();
        $notification = Notification::create([
            "user_id" => $user->id,
            "text" => $request->text,
            "status" => $request->status
        ]);
        return response()->json($notification);
    }

    public function Update(Request $request)
    {
        if ($request->has('id')) {
            $notification = Notification::where('id', $request->id)->first()->update($request->all());
        }
        return response()->json($notification);
    }

    public function Delete(Request $request)
    {
        if ($request->has('id')) {
            $notification = Notification::where('id', $request->id)->first()->update([
                "status" => 1
            ]);
        }
        return response()->json($notification);
    }

    public function Get(Request $request)
    {
	    if ($request->has('id')) {
            $notification = Notification::where('id', $request->id)->first();
        }
        return response()->json($notification);
    }

    public function GetAll(Request $request)
    {
        $user = User::where('email', $request->email)->first();
        $notification = Notification::where('user_id', $user->id)->where('status','=',0)->get();
        return response()->json($notification);
    }
}
