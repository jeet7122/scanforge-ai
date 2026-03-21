"use client";

import { useState, useRef } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

// ScanId is not used
export default function Home() {
    const [file, setFile] = useState<File | null>(null);
    const [status, setStatus] = useState("IDLE");
    const [scanId, setScanId] = useState<string | null>(null);
    const [result, setResult] = useState<any>(null);

    // ✅ keep reference to WS client (important for cleanup)
    const clientRef = useRef<Client | null>(null);

    const connectWebSocket = (id: string) => {
        console.log("🔌 Connecting WS for scan:", id);

        const socket = new SockJS("http://localhost:8083/ws");

        const client = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,

            onConnect: () => {
                console.log("✅ WS CONNECTED");

                client.subscribe(`/topic/scan/${id}`, (msg) => {
                    console.log("📩 RAW WS:", msg.body);

                    const data = JSON.parse(msg.body);

                    if (data?.type === "STATUS") {
                        setStatus(data.data.status);
                    }

                    if (data?.type === "RESULT") {
                        setResult(data.data);
                        setStatus("COMPLETED");
                    }
                });
            },

            onStompError: (frame) => {
                console.error("❌ STOMP ERROR:", frame);
            },

            onWebSocketError: (err) => {
                console.error("❌ WS ERROR:", err);
            },

            onDisconnect: () => {
                console.log("⚠️ WS DISCONNECTED");
            },
        });

        client.activate();
        clientRef.current = client;
    };

    const handleUpload = async () => {
        if (!file) return;

        setStatus("UPLOADING");

        const formData = new FormData();
        formData.append("file", file);

        try {
            const res = await fetch("http://localhost:8080/api/scan/upload", {
                method: "POST",
                body: formData,
            });

            const data = await res.json();

            console.log("📦 Scan ID:", data.scanId);

            setScanId(data.scanId);
            setStatus("REQUESTED");

            // ✅ Connect immediately (NO useEffect issues)
            connectWebSocket(data.scanId);
        } catch (err) {
            console.error("❌ Upload failed:", err);
            setStatus("FAILED");
        }
    };

    // ✅ optional cleanup (when component unmounts)
    const disconnectWebSocket = () => {
        if (clientRef.current) {
            clientRef.current.deactivate();
            clientRef.current = null;
        }
    };

    return (
        <main className="flex flex-col items-center justify-center min-h-screen gap-6">
            <h1 className="text-3xl font-bold">ScanForge 🔍</h1>

            <Input
                type="file"
                onChange={(e) => setFile(e.target.files?.[0] || null)}
            />

            <Button onClick={handleUpload}>Start Scan</Button>

            {/* ✅ Status */}
            <div className="text-lg">
                Status: <span className="font-semibold">{status}</span>
            </div>

            {/* ✅ Result */}
            {result && (
                <div className="w-full max-w-2xl mt-6">
                    <h2 className="text-xl font-bold mb-2">Issues</h2>

                    {result.issues?.map((issue: any, i: number) => (
                        <div key={i} className="border p-4 rounded mb-2">
                            <p className="font-semibold">{issue.type}</p>
                            <p className="text-sm">{issue.severity}</p>
                            <pre className="text-xs mt-2 whitespace-pre-wrap">
                {issue.snippet}
              </pre>
                        </div>
                    ))}
                </div>
            )}
        </main>
    );
}