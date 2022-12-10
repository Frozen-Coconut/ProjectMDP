<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class BloodRequest extends Model
{
    use HasFactory;

    protected $table = 'blood_requests';

    public $timestamps = false;

    protected $fillable = [
        'blood_type',
        'scheduled_date',
        'puskesmas_id'
    ];

    public function puskesmas() {
        return $this->belongsTo(User::class, 'puskesmas_id', 'id');
    }
}
