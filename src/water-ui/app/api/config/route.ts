// app/api/config/route.ts
import { NextResponse } from 'next/server';

export async function GET() {
  return NextResponse.json({
    // This is read at RUNTIME on the server
    backendUrl: process.env.BACKEND_URL || "http://localhost:8080"
  });
}