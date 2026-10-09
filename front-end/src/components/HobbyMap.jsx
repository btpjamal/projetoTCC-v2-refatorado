import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";

import markerIcon from "leaflet/dist/images/marker-icon.png";
import markerShadow from "leaflet/dist/images/marker-shadow.png";

const icon = L.icon({
    iconUrl: markerIcon,
    shadowUrl: markerShadow,
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34]
});

export default function HobbyMap({
                                     latitude,
                                     longitude,
                                     locais = []
                                 }) {
    return (
        <div className="hobby-map">
            <MapContainer
                center={[latitude, longitude]}
                zoom={12}
                scrollWheelZoom={false}
                style={{
                    width: "100%",
                    height: "420px",
                    borderRadius: "14px"
                }}
            >
                <TileLayer
                    url="https://tile.openstreetmap.org/{z}/{x}/{y}.png"
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                />

                {locais.map((local) => (
                    <Marker
                        key={local.id}
                        position={[local.latitude, local.longitude]}
                        icon={icon}
                    >
                        <Popup>
                            <strong>{local.nome}</strong>

                            {local.endereco && (
                                <p>{local.endereco}</p>
                            )}

                            <a
                                href={`https://www.google.com/maps/search/?api=1&query=${local.latitude}%2C${local.longitude}`}
                                target="_blank"
                                rel="noopener noreferrer"
                            >
                                Abrir no Google Maps ↗
                            </a>
                        </Popup>
                    </Marker>
                ))}
            </MapContainer>
        </div>
    );
}