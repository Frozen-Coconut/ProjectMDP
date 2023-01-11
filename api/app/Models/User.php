<?php

namespace App\Models;

use App\Models\BloodRequest;
use App\Models\BloodRequestUser;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class User extends Model
{
    use HasFactory;

    protected $table = 'users';
    public $timestamps = false;
    public $incrementing = true;

    protected $fillable = [
        'email',
        'password',
        'name',
        'address',
        'phone',
        'date_of_birth',
        'latitude',
        'longitude',
        'status'
    ];

    public function blood_requests() {
        return $this->hasMany(BloodRequest::class, 'puskesmas_id', 'id');
    }

    public function accept() {
        return $this->hasMany(BloodRequestUser::class, 'user_id', 'id');
    }
}
