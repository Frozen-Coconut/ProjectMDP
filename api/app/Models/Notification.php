<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Notification extends Model
{
    use HasFactory;

    protected $table = 'notifications';

    public $timestamps = false;
    public $incrementing = true;

    protected $fillable = [
        'user_id',
        'text',
        'status'
    ];
}
