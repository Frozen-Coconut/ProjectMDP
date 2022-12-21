<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class User extends Model
{
    use HasFactory;

    public $timestamps = false;

    protected $fillable = [
        'email',
        'password',
        'name',
        'address',
        'phone',
        'date_of_birth',
        'latitude',
        'longitude'
    ];

    public function blood_requests() {
        return $this->hasMany(BloodRequest::class, 'puskesmas_id', 'id');
    }
}
