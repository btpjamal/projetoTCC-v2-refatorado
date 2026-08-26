export default function RecommendationTabs({
    abaAtiva,
    setAbaAtiva
}) {

    return (
        <div className="recommendation-tabs">

            <button
                type="button"
                className={
                    abaAtiva === "descobrir"
                        ? "active"
                        : ""
                }
                onClick={() =>
                    setAbaAtiva("descobrir")
                }
            >
                Descobrir
            </button>

            <button
                type="button"
                className={
                    abaAtiva === "interessados"
                        ? "active"
                        : ""
                }
                onClick={() =>
                    setAbaAtiva("interessados")
                }
            >
                Tenho interesse
            </button>

            <button
                type="button"
                className={
                    abaAtiva === "nao-interessados"
                        ? "active"
                        : ""
                }
                onClick={() =>
                    setAbaAtiva("nao-interessados")
                }
            >
                Não me interessa
            </button>

        </div>
    );
}